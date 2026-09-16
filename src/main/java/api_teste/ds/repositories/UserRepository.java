// Organiza a classe dentro da hierarquia do projeto
package api_teste.ds.repositories;
// Dá acesso aos atributos e mapeamentos da tabela User (usuarios)
import api_teste.ds.models.User;
// Permite que a interfáce herde
// as operações do banco de e necessidade de implementar SQL de forma manual
import org.springframework.data.jpa.repository.JpaRepository;
//Importa a anotação do Spring que marca a camada de acessos dados.
import org.springframework.stereotype.Repository;

//Importa a classe utilitária Optimal do Java
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
