# ------------------------------------------
# 1) STAGE DE BUILD — Construimos Angular
# ------------------------------------------
FROM node:20-alpine AS build

WORKDIR /app

# Copiamos los archivos de configuración
COPY package*.json ./

# Instalamos dependencias
RUN npm install

# Copiamos el resto del proyecto
COPY . .

# Construimos la app Angular en modo producción
RUN npm run build



# ------------------------------------------
# 2) STAGE DE PRODUCCIÓN — Servimos con Nginx
# ------------------------------------------
FROM nginx:alpine

# Eliminamos el default.conf de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copiamos el resultado del build Angular al root de Nginx
COPY --from=build /app/dist/portal-frontend/browser /usr/share/nginx/html

# Exponemos el puerto donde corre Nginx
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
