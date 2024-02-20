<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<h1 align="center">Library Users REST API</h1>
  
  <p align="center">
    REST API powered by Spring Boot and PostgreSQL for retrieving library users
    <br />
    <a href="https://github.com/PeepStar/library-users-app"><strong>Explore the docs »</strong></a>
    <br />
    <br /> 
    <a href="https://github.com/PeepStar/library-users-app/issues">Report Bug</a>
    ·
    <a href="https://github.com/PeepStar/library-users-app/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

A refined REST API crafted for seamless user management. Engineered for scalability and simplicity in every interaction.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![SpringBoot][SpringBoot.java]][SpringBoot-url]
* [![Java][Java.java]][Java-url]
* [![PostgreSQL][PostgreSQL.sql]][PostgreSQL-url]
* [![Hibernate][Hibernate.java]][Hibernate-url]
* [![MapStruct][MapStruct.com]][MapStruct-url]
* [![Maven][Maven.com]][Maven-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This guide will help you set up and run the REST API locally on your machine.

### Prerequisites

**1. Java 17 or higher**
- Before you begin, you should check your current Java installation by using the following command:
  
 For Windows:
   ```bash
   java --version
   ```
 For Linux:
   ```bash
  $ java -version
   ```

**2. Maven**

- Compatible with Apache Maven 3.8.1 or later. If you do not already have Maven installed, you can follow the instructions at [maven.apache.org](https://maven.apache.org/download.cgi/).

**3. PostgreSQL**
- If you do not already have PostgreSQL installed, you can follow the instructions at [postgresql.org](https://www.postgresql.org/download/).


### Steps to Setup

**1. Clone the repo**
   
   ```bash
   git clone https://github.com/Peepstar/library-users-app.git
   ```

**2. Create PostgreSQL Database**

- Open your PostgreSQL client (e.g., pgAdmin or psql).
- Create Database:
 ```bash
   CREATE DATABASE "libraryBase"
   ```
- Then run `src/main/resources/libraryBase.sql`

**3. Configure PostgreSQL connection as per your installation**

- Open `src/main/resources/application.properties`
- Update `spring.datasource.username` and `spring.datasource.password` to your PostgreSQL configuration.
  
**4. Run the app using Maven**

  ```bash
  mvn spring-boot:run
  ```
The app will start running at <http://localhost:8080>


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Feature 1
- [ ] Feature 2
- [ ] Feature 3
    - [ ] Nested Feature

See the [open issues](https://github.com/PeepStar/library-users-app/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- CONTACT -->
## Contact

Julian David Peña Rojas - julianpr8@hotmail.com

Project Link: [https://github.com/PeepStar/library-users-app](https://github.com/PeepStar/library-users-app)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* []()
* []()
* []()

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/PeepStar/library-users-app.svg?style=for-the-badge
[contributors-url]: https://github.com/PeepStar/library-users-app/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/PeepStar/library-users-app.svg?style=for-the-badge
[forks-url]: https://github.com/PeepStar/library-users-app/network/members
[stars-shield]: https://img.shields.io/github/stars/PeepStar/library-users-app.svg?style=for-the-badge
[stars-url]: https://github.com/PeepStar/library-users-app/stargazers
[issues-shield]: https://img.shields.io/github/issues/PeepStar/library-users-app.svg?style=for-the-badge
[issues-url]: https://github.com/PeepStar/library-users-app/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/julian-peña-java
[product-screenshot]: images/screenshot.png
[SpringBoot.java]: https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[Java.java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/es/
[PostgreSQL.sql]: https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Hibernate.java]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Hibernate-url]: https://hibernate.org/
[MapStruct.com]: https://img.shields.io/badge/Map_Struct-blue%20?style=for-the-badge
[MapStruct-url]: https://mapstruct.org/
[Maven.com]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[Maven-url]: https://maven.apache.org/

