#!/usr/bin/env bash
set -euo pipefail
: "${CATALINA_HOME:?Defina CATALINA_HOME com a pasta extraída do Tomcat 10.1.}"
: "${DB_URL:?Execute source scripts/configurar-sessao.sh primeiro.}"
: "${DB_USER:?Configure DB_USER.}"
: "${DB_PASSWORD?Configure DB_PASSWORD, mesmo quando vazia.}"
: "${RESTAURANTE_ADMIN_PASSWORD:?Configure a senha admin.}"
: "${RESTAURANTE_GARCOM_PASSWORD:?Configure a senha garcom.}"
raiz="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
tomcat="$CATALINA_HOME"
if command -v cygpath >/dev/null 2>&1; then tomcat="$(cygpath -u "$tomcat")"; fi
war="$raiz/source/restaurante-soap/servidor/target/restaurante.war"
[[ -f "$war" ]] || { echo 'Execute mvn clean install na pasta restaurante antes.' >&2; exit 1; }
[[ -d "$tomcat/webapps" ]] || { echo 'CATALINA_HOME não aponta para uma instalação do Tomcat.' >&2; exit 1; }
cp -- "$war" "$tomcat/webapps/restaurante.war"
echo 'Iniciando Tomcat. Para parar, pressione Ctrl+C.'
cd -- "$tomcat/bin"
case "$(uname -s)" in
    MINGW*|MSYS*)
        export CATALINA_HOME="$(cygpath -w "$tomcat")"
        if [[ -n "${JAVA_HOME:-}" ]]; then export JAVA_HOME="$(cygpath -w "$JAVA_HOME")"; fi
        cmd.exe //c catalina.bat run ;;

    *) exec bash ./catalina.sh run ;;
esac
