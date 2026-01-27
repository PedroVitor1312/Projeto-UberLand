# Projeto-UberLand
UberLand é um sistema completo de gerenciamento de transporte desenvolvido em Java com interface gráfica (JavaFX). O sistema permite cadastrar passageiros, motoristas, veículos e gerenciar corridas de forma eficiente.
🎂 Receitinha de Bolo: Configurar Projeto UberLand do GitHub no Eclipse com JavaFX
📦 Ingredientes Necessários:
Eclipse IDE instalado

Java JDK 11 ou superior

JavaFX SDK 25.0.2 (ou versão compatível)

Navegador de internet

Git (opcional, pois baixaremos direto do GitHub)

📥 PASSO 1: BAIXAR PROJETO DO GITHUB (SEM CMD)
1.1 Acesse o repositório:
text
https://github.com/PedroVitor1312/Projeto-UberLand
1.2 Baixe o projeto:
Clique no botão verde "Code"

Selecione "Download ZIP"

Salve o arquivo ZIP (ex: C:\Users\pedro\Downloads\Projeto-UberLand-main.zip)

1.3 Extraia o projeto:
Clique com botão direito no ZIP → "Extrair aqui"

Mova a pasta extraída para um local organizado:

text
C:\Projetos\Projeto-UberLand
ou

text
D:\Projeto-UberLand
🛠️ PASSO 2: IMPORTAR NO ECLIPSE
2.1 Abra o Eclipse
2.2 Importe o projeto:
text
File → Import → Existing Projects into Workspace → Next
2.3 Selecione a pasta raiz:
Clique em "Select root directory" → "Browse"

Navegue até onde extraiu o projeto:

text
C:\Projetos\Projeto-UberLand
ou

text
D:\Projeto-UberLand
2.4 Finalize a importação:
Certifique-se que o projeto aparece checked na lista

Clique em "Finish"

⚙️ PASSO 3: CONFIGURAR JAVAFX
3.1 Prepare o JavaFX SDK:
Extraia o JavaFX SDK para um caminho simples:

text
C:\javafx-sdk-25.0.2
Verifique se existe a subpasta lib com arquivos .jar

3.2 Adicione os JARs ao projeto:
text
Botão direito no projeto → Properties → Java Build Path → Libraries
→ Modulepath → Add External JARs…
Navegue até: C:\javafx-sdk-25.0.2\lib

Selecione TODOS os arquivos .jar (Ctrl + A)

Clique em Open → Apply and Close

🔧 PASSO 4: CONFIGURAR VM ARGUMENTS
4.1 Crie a configuração de execução:
text
Botão direito no arquivo principal (SistemaUberLand.java)
→ Run As → Run Configurations…
4.2 Configure os argumentos:
Na aba "Arguments"

Em "VM arguments", cole:

text
--module-path "C:\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml
Clique em Apply → Close

📝 PASSO 5: AJUSTAR module-info.java
5.1 Abra o arquivo module-info.java
5.2 Configure assim:
java
module ProjetoUberLand {
    requires javafx.controls;
    requires javafx.fxml;

    opens controller to javafx.fxml;
    opens model to javafx.fxml;
    
    exports controller;
    exports model;
}
Nota: Ajuste controller e model conforme os nomes dos pacotes no seu projeto.

🚀 PASSO 6: RODAR O PROJETO
6.1 Execute:
text
Botão direito no arquivo principal → Run As → Java Application
6.2 Verifique:
A janela do JavaFX deve abrir sem erros

Interface gráfica funcionando corretamente

⚠️ SOLUÇÃO PARA PROBLEMAS COMUNS
🔧 Se module-info.java não for reconhecido:
Opção A: Converter para projeto modular:
text
Botão direito no projeto → Properties → Project Facets
→ Marque apenas "Java" (versão do seu JDK)
→ Se aparecer: "Convert to Modular Project" ou "Enable Project Module Support"
Opção B: Configurar manualmente:
Vá em: Java Build Path → Source

Deve aparecer src com ícone de módulo (cubo pequeno)

Se não aparecer:

Crie pasta module

Mova module-info.java para ela

Clique com direito na pasta → Build Path → Use as Module Source Folder

🔧 Se houver erros de dependência:
Verifique se todos os JARs do JavaFX estão no Modulepath

Confira se o caminho no VM Arguments está correto

Certifique-se que a versão do JavaFX é compatível com seu JDK

✅ 6.1 Rodar Normalmente (Se tudo estiver configurado):
text
Botão direito no arquivo principal (SistemaUberLand.java)
→ Run As → Java Application
A interface JavaFX deve abrir sem erro.

⚠️ 6.2 Se Não Funcionar (Problemas Comuns):
🔍 Cenário: module-info.java não é reconhecido
Solução A: Configurar Project Facets
text
1. Botão direito no projeto → Properties → Project Facets
2. Marque apenas "Java" (versão 23 ou sua versão do JDK)
3. **DESMARQUE "Dynamic Web Module"** (não é necessário)
4. Se aparecer a opção: 
   - "Convert to Modular Project" ou 
   - "Enable Project Module Support"
   → CLIQUE!
5. Clique em Apply and Close
Resultado Esperado:
O src será marcado como Module Source Folder

Verifique em: Java Build Path → Source

Deve aparecer Projeto/src com ícone de módulo (pequeno cubo)

Solução B (Alternativa - Manual):
Se NÃO aparecer "Convert to Modular Project":

text
1. Crie uma nova pasta no projeto chamada "module"
2. Mova o arquivo "module-info.java" para dentro dela
3. Clique com botão direito na pasta "module"
4. Selecione: Build Path → Use as Module Source Folder
🔄 6.3 Reconfigure Após Correção:
Após qualquer correção acima, REPITA:

Adicione os JARs do JavaFX novamente:

text
Properties → Java Build Path → Libraries
→ Modulepath → Add External JARs…
→ C:\javafx-sdk-25.0.2\lib (selecione todos .jar)
Configure VM Arguments novamente:

text
--module-path "C:\javafx-sdk-25.0.2\lib" 
--add-modules javafx.controls,javafx.fxml
Verifique module-info.java:

Certifique-se que está correto (como no Passo 5)

Os pacotes (controller, model) correspondem ao seu projeto

🎯 6.4 Teste Final:
text
Botão direito no arquivo principal 
→ Run As → Java Application
O projeto deve rodar normalmente!
