package com.gugawag.pdist.ejbs;

import com.gugawag.pdist.model.Mensagem;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Arrays;

@Stateless(name = "mensagemService")
@Remote
public class MensagemService {

    private static final List<String> PROFANITY_LIST = Arrays.asList("badword1", "badword2", "badword3");

    @EJB
    private MensagemDAO mensagemDao;

    public List<Mensagem> listar() {
        return mensagemDao.listar();
    }

    public void inserir(long id, String mensagem) {
        if (containsProfanity(mensagem)) {
            throw new RuntimeException("Mensagem contém palavrões e não pode ser inserida.");
        }

        if (id == 3L) {
            throw new RuntimeException("ID 3 não permitido Palavrões.");
        }

        Mensagem novaMensagem = new Mensagem(id, mensagem);

        if (id == 4L) {
            novaMensagem.setMensagem(mensagem + " alterado");
        }

        mensagemDao.inserir(novaMensagem);
    }

    private boolean containsProfanity(String mensagem) {
        return PROFANITY_LIST.stream().anyMatch(mensagem.toLowerCase()::contains);
    }
}
