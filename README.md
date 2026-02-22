# ai-whatsapp-agent-service

Production-grade WhatsApp AI Agent built using:

* Spring Boot 3
* Ollama (Local LLM – FREE)
* MySQL
* Redis
* Qdrant (Vector DB)
* Docker
* Prometheus + Grafana
---

#  System Architecture

```bash
                         ┌─────────────────────────┐
                         │     WhatsApp User       │
                         └────────────┬────────────┘
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │  Meta WhatsApp Cloud    │
                         │       (Free Tier)       │
                         └────────────┬────────────┘
                                      │ Webhook
                                      ▼
┌────────────────────────────────────────────────────────────────┐
│                 ai-whatsapp-agent-service                      │
│                                                                │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                  Webhook Controller                      │  │
│  └──────────────────────────┬───────────────────────────────┘  │
│                             │                                   │
│                             ▼                                   │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                    Agent Service                          │  │
│  │                                                          │  │
│  │  1. Rate Limiting                                       │  │
│  │  2. Load Memory (Redis + MySQL)                         │  │
│  │  3. Tool Detection                                      │  │
│  │  4. RAG Search                                          │  │
│  │  5. Prompt Builder                                      │  │
│  │  6. LLM Call (Ollama)                                   │  │
│  │  7. Save Conversation                                   │  │
│  └───────────────┬───────────────┬─────────────────────────┘  │
│                  │               │                            │
│                  ▼               ▼                            │
│         ┌─────────────┐   ┌───────────────┐                   │
│         │   Redis     │   │    MySQL      │                   │
│         │ Short-Term  │   │ Long-Term DB  │                   │
│         └─────────────┘   └───────────────┘                   │
│                                                                │
│                  ▼                                              │
│         ┌──────────────────────────┐                           │
│         │        Qdrant            │                           │
│         │     (Vector Search)      │                           │
│         └──────────────────────────┘                           │
│                                                                │
│                  ▼                                              │
│         ┌──────────────────────────┐                           │
│         │         Ollama           │                           │
│         │     (Llama3 Model)       │                           │
│         └──────────────────────────┘                           │
│                                                                │
│                  ▼                                              │
│         ┌──────────────────────────┐                           │
│         │   WhatsApp Send API      │                           │
│         └──────────────────────────┘                           │
└────────────────────────────────────────────────────────────────┘
```

---

#  Feature Overview

##  Core Features

* WhatsApp Webhook Integration
* Local LLM (Ollama – llama3)
* RAG (Retrieval Augmented Generation)
* Tool Calling (Inventory, FAQ, Orders)
* Redis Short-Term Memory
* MySQL Persistent Memory
* Rate Limiting (Bucket4j)
* Structured Logging
* Global Exception Handling
* Monitoring with Prometheus
* Dockerized Deployment

---

# Project Structure

```bash
ai-whatsapp-agent-service/
│
├── controller/
├── service/
├── llm/
├── rag/
├── repository/
├── model/
├── config/
├── exception/
├── monitoring/
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

#  Infrastructure Setup

## 1️. Start Infrastructure

```bash
docker compose up -d
```

Services started:

* MySQL
* Redis
* Qdrant
* Ollama

---

## 2️. Install LLM Models

```bash
docker exec -it ollama ollama pull llama3
docker exec -it ollama ollama pull nomic-embed-text
```

---

#  WhatsApp Setup

1. Create Meta Developer Account
2. Add WhatsApp product
3. Get:

   * Phone Number ID
   * Access Token
4. Set Webhook URL:

```bash
ngrok http 8080
```

Use generated HTTPS URL as webhook.

---

#  Agent Processing Flow

```bash
User Message
     ↓
Webhook Controller
     ↓
Validate Signature
     ↓
Rate Limit Check
     ↓
Load Redis Memory
     ↓
RAG Vector Search
     ↓
Tool Detection
     ↓
Build Prompt
     ↓
Call Ollama (LLM)
     ↓
Save Conversation
     ↓
Send WhatsApp Reply
```
