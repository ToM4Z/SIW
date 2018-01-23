package persistence.dao;

import java.util.List;
import model.Post;
import model.Utente;

public interface PostDao {
	
	public void save(Post post);
	public Post findByPrimaryKey(Long id);
	public List<Post> findAll();       
	public void update(Post post); 
	public void delete(Post post);
	public void addLike(Post post, Utente utente);
	public void removeLike(Post post, Utente utente);
	public void addDislike(Post post, Utente utente);
	public void removeDislike(Post post, Utente utente);

}
