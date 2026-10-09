# EcoPulse Frontend Dockerfile
FROM nginx:alpine

# Copy static assets to Nginx html directory
COPY . /usr/share/nginx/html

# Expose HTTP port 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
