# 📚 Sistema de Gestión de Biblioteca

## Descripción

Este proyecto es un sistema de gestión para bibliotecas que permite administrar usuarios, libros, préstamos y más. Facilita el control de inventario de libros, el seguimiento de préstamos y la gestión de usuarios.

## Características principales

- **Gestión de usuarios**: Registro, modificación y eliminación de usuarios
- **Catálogo de libros**: Administración completa del inventario de libros
- **Control de préstamos**: Seguimiento de préstamos y devoluciones
- **Gestión de ejemplares**: Control del estado físico de cada ejemplar
- **Administración de autores**: Registro y gestión de información de autores

## Requisitos

- Java 21 o superior
- Conexión a internet para acceder a la base de datos remota

## Instrucciones de uso

### Iniciar sesión

1. Ejecute la aplicación
2. Seleccione la opción "Login"
3. Introduzca su DNI y contraseña
4. Acceda al sistema

### Registrarse como nuevo usuario

1. Ejecute la aplicación
2. Seleccione la opción "Registrarse"
3. Complete el formulario con sus datos personales
4. Su cuenta será creada con permisos de usuario normal

### Menú principal

Una vez iniciada sesión, tendrá acceso a las siguientes opciones:

- **Libros**: Consulta, registro y gestión de libros
- **Usuarios**: Administración de usuarios
- **Préstamos**: Control de préstamos activos
- **Ejemplares**: Gestión del estado de los ejemplares
- **Autores**: Registro y consulta de información de autores

### Gestión de libros

- Consultar el catálogo completo
- Añadir nuevos libros al sistema
- Eliminar libros del catálogo
- Buscar libros por ISBN

### Préstamos

- Registrar nuevos préstamos
- Consultar préstamos activos
- Gestionar devoluciones

## Administración de Usuarios

- Ver listado de usuarios
- Añadir o elimiar usuarios
- Buscar usuarios penalizados
- 
## Notas importantes

- La fecha de devolución se calcula automáticamente (15 días a partir del préstamo)
- Los usuarios con penalizaciones no podrán realizar nuevos préstamos
- Es necesario mantener actualizado el estado físico de los ejemplares
