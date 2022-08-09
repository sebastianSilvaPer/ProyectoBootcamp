db = db.getSiblingDB('cursos');

db.createCollection('Usuario');

db.Usuario.insertMany([
    {
        _id: UUID('08190c52-ac2d-4b2c-a6f5-e47697f16542'),
        nombre: 'sebastian',
        apellido: 'silva',
        correo: 'sebas@gmail.com',
        clave: '$2a$12$IvJl4f11rHpbw.xkoCINhO0gsidOVVtF6ZRe5Z.4Pkto/ZTgDVUz2',
        rol: 'ROL_ADMIN',
        borrado: false,
        _class: 'com.proyecto.bootcamp.DAO.Document.Usuario'
      }
]);