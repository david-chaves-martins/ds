package api_teste.ds.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column; //Anotações para configurar restrições de coluna de tabela
import jakarta.persistence.Entity; //Anotação para sinalizar que a classe é uma entidade JPA
import jakarta.persistence.GeneratedValue; //Anotação para persistir a estretégia de geração de ID
import jakarta.persistence.GenerationType; //Anotação para geração automática de chaves
import jakarta.persistence.Id; //Anotação para indicar chave primária
import jakarta.persistence.Table; // Anotação para especificar o nome da tabela

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity //define que essa classe será mapeada para uma tabela do banco de dados
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {
    }

    public interface UpdateUser {
    }

    public static final String TABLE_NAME = "users";

    //Mapeamento da Chave Primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //configura o campo Id como AUTO_INCREMENT no MySql
    private Long Id; //declara o identificador unico da entidade

    //mapeamento e validação do nome do usuario

    @Column(nullable = false, length = 100) // configura a coluna nome como NOT_NULL e tamanho máximo de 100
    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 100)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class}, min = 8, max = 60)
    private String password;

    public User() {
        //vazio sem argumentos (Obrigatório para o funcionamento do JPA/Hibernate
    }

    public User(Long id, String username, String password) {
        this.Id = id;
        this.username = username;
        this.password = password;

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override // Informa ao compilador que estamos sobrescrevendo o método 'equals' da classe base Object
    public boolean equals(Object obj) { // Assinatura do método: recebe um objeto genérico 'obj' e retorna true ou false

        // 1. Validação por referência na memória
        if (this == obj) // Se 'this' e 'obj' apontam para o mesmo endereço de memória, são a mesma instância
            return true; // Retorna true imediatamente (ganho de performance)

        // 2. Validação de nulo
        if (obj == null) // Se o objeto passado para comparação for nulo
            return false; // Retorna false, pois a instância atual ('this') não é nula

        // 3. Validação de tipo/classe
        if (!(obj instanceof User)) // Verifica se 'obj' NÃO é uma instância da classe User
            return false; // Retorna false se forem de classes diferentes (ex: comparar User com String)

        // 4. Conversão de tipo (Casting)
        User other = (User) obj; // Converte 'obj' de Object para User para acessar os atributos da classe User

        // 5. Comparação do atributo 'id'
        if (this.Id == null) { // Caso o ID do objeto atual ('this') seja nulo
            if (other.Id != null) // E o ID do outro objeto ('other') NÃO seja nulo
                return false; // Retorna false porque um tem ID e o outro não
        } else if (!this.Id.equals(other.Id)) // Se o ID do objeto atual não for nulo e for diferente do ID do outro
            return false; // Retorna false porque os IDs são diferentes

        // 6. Comparação dos atributos 'username' e 'password'
        // 'Objects.equals' compara os valores tratando o risco de NullPointerException caso algum campo seja nulo.
        // Retorna true apenas se TANTO o username QUANTO o password de ambos os objetos forem iguais.
        return Objects.equals(this.username, other.username) && Objects.equals(this.password, other.password);
    }
}