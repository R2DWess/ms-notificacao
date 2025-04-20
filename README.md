<p align="center">
  <img src="https://iili.io/3FFO5cF.png" alt="Universidade Católica de Brasília">
</p>

## PROJETO PESSOAL - MICROSSERVIÇO  📚:

#### 📖 Descrição:
Microsserviço de **notificação e processamento de compras**, responsável por interagir com outros microsserviços para realizar a orquestração de uma compra. Ele consome o microsserviço de catálogo (`ms-catalogo`) para obter dados dos produtos e interage com o microsserviço de comprovantes (`ms-comprovante`) para registrar o comprovante de uma compra.

#### ⚡ Funcionalidades:
1. 🛍️ Realização de compras a partir de uma lista de produtos;
2. 📉 Consulta de dados dos produtos via integração com o `ms-catalogo`;
3. 📋 Geração de resposta com detalhes da compra realizada;
4. 📢 Integração com outros microsserviços para notificação (em expansão);

#### Métodos de execução:

### 🖥️ **1️⃣ Rodar Localmente**
Para executar o projeto localmente, siga os passos abaixo:

> ⚡ Este projeto **não utiliza banco de dados**.

1. Clone o repositório e navegue até a pasta do projeto.
2. Garanta que o `ms-catalogo` esteja rodando na porta `8081` com o endpoint `/v1/produtos/id/{id}` acessível.
3. Rode o projeto com o comando:

```sh
./gradlew bootRun
```

> ⚠ A aplicação está configurada para rodar na porta `8082`.

### 🔧 Tecnologias utilizadas:
- ☕ Java 21;
- 🍃 Spring Boot;
- 📈 WebClient com WebFlux;
- 📓 Gradle;

---

## 📌 Endpoints e exemplos de uso:

### 🛎️ 1 - Realizar Compra:
```cmd
curl --request POST \
  --url http://localhost:8082/v1/compras \
  --header 'Content-Type: application/json' \
  --data '[1, 2, 3]'
```
**Resposta esperada:**
```json
{
  "mensagem": "Compra processada com sucesso!",
  "produtos": [
    {
      "id": 1,
      "title": "Notebook Dell",
      "price": 3500.00,
      "category": "Eletrônicos"
    },
    {
      "id": 2,
      "title": "Mouse Logitech",
      "price": 150.00,
      "category": "Acessórios"
    }
  ]
}
```

## 🚊 Autor

<table>
  <tr>
    <td align="center">
      <a href="https://www.linkedin.com/in/wesley-lima-244405251/" title="Wesley Lima">
        <img src="https://media.licdn.com/dms/image/v2/D4D03AQEVAsL2UL6A0w/profile-displayphoto-shrink_400_400/profile-displayphoto-shrink_400_400/0/1721323972268?e=1746662400&v=beta&t=4_2RDPgz5FqJ2G-yRQk3y0vWMVRpSeAPKMAO7IOFXeE" width="100px;" alt="Foto do Wesley Lima"/><br>
        <sub>
          <b>Wesley Lima</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

