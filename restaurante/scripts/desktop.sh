#!/usr/bin/env bash
set -euo pipefail
raiz="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
case "${1:-}" in
    admin) classe=br.edu.soa.restaurante.TelaProdutos ;;
    garcom) classe=br.edu.soa.restaurante.TelaPedidos ;;
    *) echo 'Uso: bash scripts/desktop.sh admin|garcom' >&2; exit 1 ;;
esac
exec mvn -f "$raiz/source/restaurante-soap/desktop/pom.xml" exec:java "-Dexec.mainClass=$classe"
