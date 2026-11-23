package cl.sakuramarket.sakura_market_backend.dto;

import cl.sakuramarket.sakura_market_backend.model.Usuario;

public class Respuestas {

    public static class AuthResponse {
        private String token;
        private Usuario usuario;

        public AuthResponse(String token, Usuario usuario) {
            this.token = token;
            this.usuario = usuario;
        }

        public String getToken() { return token; }
        public Usuario getUsuario() { return usuario; }
    }

    public static class MensajeResponse {
        private String mensaje;

        public MensajeResponse(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() { return mensaje; }
    }
}