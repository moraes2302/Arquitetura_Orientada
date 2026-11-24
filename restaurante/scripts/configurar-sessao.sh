#!/usr/bin/env bash
# Use: source scripts/configurar-sessao.sh (para conservar as variáveis na sessão).
configurar_sessao_restaurante() {
    local entrada senha_admin senha_garcom senha_banco
    read -r -p 'URL JDBC [jdbc:mysql://localhost:3306/restaurante_soap?connectionTimeZone=UTC]: ' entrada
    export DB_URL="${entrada:-jdbc:mysql://localhost:3306/restaurante_soap?connectionTimeZone=UTC}"
    read -r -p 'Usuário MySQL [root]: ' entrada
    export DB_USER="${entrada:-root}"
    read -r -s -p 'Senha desse usuário MySQL (Enter se vazia): ' senha_banco; echo
    read -r -s -p 'Escolha a senha admin (mínimo 12 caracteres): ' senha_admin; echo
    read -r -s -p 'Escolha outra senha garcom (mínimo 12 caracteres): ' senha_garcom; echo
    if [[ ${#senha_admin} -lt 12 || ${#senha_garcom} -lt 12 || "$senha_admin" == "$senha_garcom" ]]; then
        echo 'As duas senhas devem ser diferentes e ter pelo menos 12 caracteres. Repita a configuração.' >&2
        return 1
    fi
    export DB_PASSWORD="$senha_banco"
    export RESTAURANTE_ADMIN_PASSWORD="$senha_admin"
    export RESTAURANTE_GARCOM_PASSWORD="$senha_garcom"
    export PUBLIC_BASE_URL="${PUBLIC_BASE_URL:-http://localhost:8080/restaurante}"
    export SOAP_BASE_URL="${SOAP_BASE_URL:-http://localhost:8080/restaurante}"
    echo 'Sessão configurada. Nenhuma senha foi gravada em arquivo.'
}
configurar_sessao_restaurante
