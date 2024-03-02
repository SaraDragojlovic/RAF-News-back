package com.example.demo;

import com.example.demo.Repositories.Category.CategoryRepository;
import com.example.demo.Repositories.Category.MySqlCategoryRepository;
import com.example.demo.Repositories.Comment.CommentRepository;
import com.example.demo.Repositories.Comment.MySqlCommentRepository;
import com.example.demo.Repositories.News.MySqlNewsRepository;
import com.example.demo.Repositories.News.NewsRepository;
import com.example.demo.Repositories.News_Tags.MySqlNews_TagsRepository;
import com.example.demo.Repositories.News_Tags.News_TagsRepository;
import com.example.demo.Repositories.Tag.MySqlTagRepository;
import com.example.demo.Repositories.Tag.TagRepository;
import com.example.demo.Repositories.User.MySqlUserRepository;
import com.example.demo.Repositories.User.UserRepository;
import com.example.demo.Services.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        String hashedPassword = DigestUtils.sha256Hex("sara");
        System.out.println(hashedPassword);

        packages("com.example.demo");

        register(CORSFilter.class);
    }

    @PostConstruct
    public void setUp() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                //bajndovanje implementacije za ovaj repozitorijum
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);
                this.bind(MySqlNews_TagsRepository.class).to(News_TagsRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(News_TagsService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(UserService.class);
            }
        };
        register(binder);
    }
}
