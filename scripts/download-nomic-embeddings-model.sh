#!/bin/bash -ex

SOURCE_URL="https://huggingface.co/nomic-ai/nomic-embed-text-v1/resolve/main/onnx/model_quantized.onnx?download=true"

SCRIPT_DIRECTORY="${0%/*}"
TARGET_DIRECTORY="${SCRIPT_DIRECTORY}/../src/main/resources/embeddings/nomic/model.onnx"

# --location to follow redirects
curl --verbose --location --output "$TARGET_DIRECTORY" "$SOURCE_URL"

