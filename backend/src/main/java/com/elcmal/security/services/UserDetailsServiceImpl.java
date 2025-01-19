package com.elcmal.security.services;

import com.elcmal.model.User;
import com.elcmal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] msgpAram={};
        String msg=messageSource.getMessage("validation.usernotfoundwithusername.message",msgpAram, LocaleContextHolder.getLocale());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(msg + username));

        return UserDetailsImpl.build(user);
    }
}
