# ---- Stage 1 : build ----
FROM node:24-alpine AS build
WORKDIR /app
 
# Dépendances d'abord (cache Docker)
COPY package*.json ./
RUN npm ci
 
COPY . .
 
# URL de l'API : relative, nginx fait le proxy vers le backend
ARG VITE_API_URL=/api
ENV VITE_API_URL=$VITE_API_URL
RUN npm run build
 
# ---- Stage 2 : runtime ----
FROM nginx:stable-alpine
 
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
 
EXPOSE 80
 
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD wget -qO- http://localhost/ >/dev/null || exit 1
 