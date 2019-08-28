package telran.java29.forum.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import telran.java29.forum.dao.PostRepository;
import telran.java29.forum.dao.UserAccountRepository;
import telran.java29.forum.domain.Post;
import telran.java29.forum.domain.UserAccount;
import telran.java29.forum.dto.PostUpdateDto;

@Component("customSecurity")
public class CustomWebSecurity {

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	PostRepository postRepository;

	public boolean checkAuthorityForDeletePost(Authentication authentication, String id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post == null) {
			return false;
		}
		UserAccount userAccount = userAccountRepository.findById(authentication.getName()).get();
		boolean checkOwner = authentication.getName().equals(post.getAuthor()) 
				&& !authentication.getAuthorities().isEmpty();
		
		
		boolean	checkModerator = userAccount.getRoles().contains("ROLE_MODERATOR");
		
		return checkOwner || checkModerator;
	}
	
	
	public boolean checkAuthorityForUpdatePost(Authentication authentication, String id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post == null) {
			return false;
		}
		UserAccount userAccount = userAccountRepository.findById(authentication.getName()).get();
		boolean checkOwner = authentication.getName().equals(post.getAuthor()) 
				&& !authentication.getAuthorities().isEmpty();
		
			
		return checkOwner;
	}
	
	public boolean checkAuthorityForAddLike(Authentication authentication, String id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post == null) {
			return false;
		}
		
		UserAccount userAccount = userAccountRepository.findById(authentication.getName()).orElse(null);
		if(userAccount == null)
		{
			return false;
		}
			
		return true;
	}
	
	

}
