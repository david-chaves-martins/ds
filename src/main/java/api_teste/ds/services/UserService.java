package api_teste.ds.services;

import api_teste.ds.models.User;
import api_teste.ds.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException(
                "Usuario não encontrado. Id: " + id +
                        " Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create(User obj) {
    obj.setId(null);
    obj =this.userRepository.save(obj);
    this.taskRepository.saveAll(obj.getClass());
    return obj;
    }
    @Transactional
    public User update(User obj){
    User newObj = findById(obj.getId());
    }
}