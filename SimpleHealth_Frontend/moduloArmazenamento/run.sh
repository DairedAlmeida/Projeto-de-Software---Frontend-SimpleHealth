#!/bin/bash

echo "========================================"
echo "SimpleHealth - Módulo Armazenamento"
echo "========================================"
echo

echo "Verificando se o Maven está instalado..."
if ! command -v mvn &> /dev/null; then
    echo "ERRO: Maven não encontrado. Instale o Maven primeiro."
    exit 1
fi

echo "Maven encontrado!"
echo

echo "Compilando o projeto..."
mvn clean compile
if [ $? -ne 0 ]; then
    echo "ERRO: Falha na compilação."
    exit 1
fi

echo
echo "Executando a aplicação..."
echo "IMPORTANTE: Certifique-se de que o backend Spring Boot esteja rodando na porta 8080"
echo
read -p "Pressione Enter para continuar..."

mvn javafx:run

echo
echo "Aplicação finalizada."