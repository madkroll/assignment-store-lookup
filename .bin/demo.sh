#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

curl -X GET "http://localhost:8080/lookup/pickup?latitude=51.503687&longitude=5.414346" \
  -H "Accept: application/json" | jq
