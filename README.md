# CRUD without using framework

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

This project is a simple football player crud api using only java. The purpose of the project was to learn more in depth how an API is built without the help of frameworks. A postgres database was used to store the data.

# How to run

To run this project you need to clone this repository using:
```bash
  git clone github.com/gabrielferreira02/crud_without_framework.git
  cd crud_without_framework
```

Then create a .env file like this in root folder:
```bash
  BASE_URL=jdbc:postgresql://localhost:5432/
  USER=your_user
  PASSWORD=your_password
```

Now you can start the project on your IDE from the Main class, and access in http://localhost:8080/api
