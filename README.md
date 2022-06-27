# cas-backend
cas-backend

## Prerequisite
- Java11 or above.
- maven 3.x.x or above.

## Usage
1. modify the [configuration file](src/main/resources/application.yml) according to your actual situation.
2. Run the application by executing the following command.
    ```shell
    mvn clean
    mvn package
    java -jar project_name
    ```

## Doc
- backend-API: https://www.eolink.com/share/index?shareCode=FqneKi
- project-UI: https://modao.cc/app/Q8fZsXradhqgS7lgs4RF#screen=sl20hl6epj447lm

## TODO
- [ ] optimize sql query clause
    - [ ] use `union` for ca interface.
    - [ ] use `view` and `join`.
    - [ ] Merge multiple SQL clauses.
- [ ] use transaction
- [ ] refactor code
    - [ ] canonical naming
    - [ ] canonical interface

## License
在**Apache-2.0**许可证下发布。有关更多信息，请参阅[LICENSE](LICENSE)。
