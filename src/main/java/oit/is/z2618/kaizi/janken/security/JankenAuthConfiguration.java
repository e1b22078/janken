package oit.is.z2618.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class JankenAuthConfiguration {

  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http HttpSecurityのインスタンス
   * @return SecurityFilterChainのインスタンス
   * @throws Exception 認可設定中に発生する可能性のある例外
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(AntPathRequestMatcher.antMatcher("/janken/**")).authenticated() // /janken/**は認証必須
            .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll() // その他は全員アクセス可能
        )
        .formLogin(login -> login.permitAll()) // ログインは全員許可
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/") // ログアウト後に / にリダイレクト
        )
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))) // h2-console用にCSRF対策を無効化
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions.sameOrigin())); // H2コンソールのための設定

    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return InMemoryUserDetailsManagerのインスタンス
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    // ユーザ名，パスワード，ロールを指定してbuildする
    UserDetails user1 = createUser("user1", "$2y$05$SjTq9jlhu3hQVQvMqz.yfub.yRwd7Pa4N9FpVOl.g8EzyA9XgzvCa", "USER");
    UserDetails user2 = createUser("user2", "$2y$05$Pav./mLKQN4fBIrz42wMrekLY17iRpzijVVcUf.BadrgAPSScPco.", "USER");
    UserDetails user3 = createUser("ほんだ", "$2y$05$ffljCY9ZOEYOQposDuDqU.tt/0v.yitodF9YYq62naioz8iXoTMsy", "USER");
    UserDetails user4 = createUser("いがき", "$2y$05$ljkkNFivOjQ0xKleXeoOzusItjddF5zmEgcZZ2xfBaD1yy.lqzmci", "USER");

    // 生成したユーザをInMemoryUserDetailsManagerに渡す
    return new InMemoryUserDetailsManager(user1, user2, user3, user4);
  }

  private UserDetails createUser(String username, String password, String... roles) {
    return User.withUsername(username)
        .password("{bcrypt}" + password) // bcryptプレフィックスを追加
        .roles(roles)
        .build();
  }
}
