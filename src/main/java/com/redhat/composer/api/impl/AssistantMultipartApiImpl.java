package com.redhat.composer.api.impl;

import com.redhat.composer.api.AssistantMultipartApi;
import com.redhat.composer.api.mapper.AssistantMapper;
import com.redhat.composer.api.model.AssistantChatMessage;
import com.redhat.composer.api.model.AssistantChatRequestMultipart;
import com.redhat.composer.services.ChatBotService;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import org.apache.tika.metadata.Metadata;

/**
 * Assistant API for Chatting using assistants.
 */
@Authenticated
public class AssistantMultipartApiImpl implements AssistantMultipartApi {

  @Inject
  AssistantMapper restMapper;

  @Inject
  ChatBotService chatBotService;

  /**
   * Chat with an assistant.
   *
   * @param multipartRequest the multipart request which includes a AssistantChatMessage
   * @return the Multi of String
   */
  @Override
  public Multi<String> assistantChatStreamingMp(AssistantChatRequestMultipart multipartRequest) {
    AssistantChatMessage chatRequest = multipartRequest.getJsonRequest();

    return chatBotService.chat(
        restMapper.fromRest(chatRequest),
        multipartRequest.getDocument()
            .stream()
            .map(fileUpload -> {
              ApacheTikaDocumentParser parser = new ApacheTikaDocumentParser(
                  ApacheTikaDocumentParser.DEFAULT_PARSER_SUPPLIER,
                  ApacheTikaDocumentParser.DEFAULT_CONTENT_HANDLER_SUPPLIER,
                  () -> {
                    Metadata metadata = ApacheTikaDocumentParser.DEFAULT_METADATA_SUPPLIER.get();

                    metadata.add("title", fileUpload.fileName());
                    metadata.add("file_name", fileUpload.fileName());
                    metadata.add("file_size", String.valueOf(fileUpload.size()));
                    metadata.add("content_type", fileUpload.contentType());
                    metadata.add("charset", fileUpload.charSet());
                    // TODO: The source should be a URL, right? Once uploads are persisted, this can be changed
                    metadata.add("source", "User Upload");

                    return metadata;
                  },
                  ApacheTikaDocumentParser.DEFAULT_PARSE_CONTEXT_SUPPLIER
              );

              return FileSystemDocumentLoader.loadDocument(fileUpload.filePath(), parser);
            })
            .toList()
    );
  }
}
