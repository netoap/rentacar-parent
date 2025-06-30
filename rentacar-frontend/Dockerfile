# Etapa 1: construir la aplicación
FROM node:20-alpine as builder
WORKDIR /app
COPY ../../../../../AppData/Local/Temp .
RUN npm install && npm run build

# Etapa 2: servir la aplicación con Nginx
FROM nginx:alpine
COPY --from=builder /app/dist/rentacar-frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
