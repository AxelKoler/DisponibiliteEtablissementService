# ---- Stage 1 : build ----
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# ---- Stage 2 : serve avec nginx ----
FROM nginx:alpine

# Adapte le dossier source selon ton bundler :
# - Create React App -> /app/build
# - Vite            -> /app/dist
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
