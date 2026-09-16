package api_teste.ds.services;

import api_teste.ds.models.Task;
import api_teste.ds.models.User;
import api_teste.ds.repositories.TaskRepository;
import api_teste.ds.repositories.UserRepository;
import jakarta.persistence.Id;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {
    //Injeta automaticamente a instância do TaskRepository gerenciado pelo SpringBoot
    @Autowired
    private TaskRepository taskRepository;

    //Injeta automaticamentea instância do UserService para validar o usuario
    @Autowired
    private UserService userService;

    //Metódo para buscar task a partir do ID
    public Task findById(Long Id){
        //Executa a busca no banco, retorna um Optional contendo (ou não) a Task
        Optional<Task> task = this.taskRepository.findById(Id);
        return task.orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada! Id: " +Id + "Tipo: " + Task.class.getName()
        ));
    }

    //Método para buscar todas as tarefas vinculados a um determinado usuário
    public List<Task> findByUserId(Long UserId){
        //Chama o UserService para garantir que o usuario existe no banco(lança exceção se não existir)
        this.userService.findById(UserId);

        //executa a busca customizada no repositório filtrando pelo id do usuário
        List<Task> tasks = this.taskRepository.findByUser_Id(UserId);
        //Retorna a lista de tarefas
            return tasks;
    }

    @Transactional
    public Task create  (@NonNull Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        //Define o Id como null para garantir que o JPA realize uma inserção(INSERT) e não uma atualização
        obj.setId(null);
        //Associa a entidade User completa e valida a tarefa
        obj.setUser(user);
        //Salva a nva tarefa no banco de dados e atualiza 'obj' com o ID gerado
        obj = this.taskRepository.save(obj);

        //Retorna a tarefa salva
        return obj;
    }

    //Garante que a atualização ocorra dentro de transação isolada no banco
    @Transactional
    public Task update (Task obj){
        //Reaproveita o findByID para verificar se a tarefa a ser atualizada existe realmente
        Task newObj = findById(obj.getId());

        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }
//Método para  deletar uma tarefa por ID
    public void delete(Long Id){
        //verifica se a tarefa existe antes de tentar deletar
        findById(Id);
        try{
            //solicita a remoção da tarefa no banco de dados pelo ID
            this.taskRepository.deleteById(Id);
        } catch (Exception e){
            //caputura exceções (como violações de chave estrangeira e lança uma mensagem amigável
          throw new  RuntimeException("Não é possível excluir pois não há tarefas relacionadas");
        }
    }


}
