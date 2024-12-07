package api.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // Membuat konfigurasi CORS
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Izinkan penggunaan cookie atau credential
        config.addAllowedOriginPattern("*"); // Mengizinkan semua origin, atau ganti dengan URL spesifik seperti
                                             // "http://localhost:3000"
        config.addAllowedHeader("*"); // Izinkan semua header
        config.addAllowedMethod("*"); // Izinkan semua metode HTTP (GET, POST, PUT, DELETE, dll.)

        // Menambahkan konfigurasi ke semua endpoint
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Terapkan ke semua path
        return new CorsFilter(source);
    }
}
