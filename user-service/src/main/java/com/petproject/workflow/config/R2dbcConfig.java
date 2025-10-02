package com.petproject.workflow.config;

import com.petproject.workflow.store.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

@Configuration
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions customConversions() {
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE,
                Arrays.asList(
                        // UUID конвертеры
                        new UuidToByteArrayConverter(),
                        new ByteArrayToUuidConverter(),

                        // Enum конвертеры
                        new RoleToStringConverter(),
                        new StringToRoleConverter()
                ));
    }

    // Enum конвертеры
    @WritingConverter
    public static class RoleToStringConverter implements Converter<Role, String> {
        @Override
        public String convert(Role source) {
            return source.name();
        }
    }

    @ReadingConverter
    public static class StringToRoleConverter implements Converter<String, Role> {
        @Override
        public Role convert(String source) {
            return Role.valueOf(source);
        }
    }

    // UUID конвертеры
    @WritingConverter
    public static class UuidToByteArrayConverter implements Converter<UUID, byte[]> {
        @Override
        public byte[] convert(UUID source) {
            if (source == null) {
                return null;
            }
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(source.getMostSignificantBits());
            bb.putLong(source.getLeastSignificantBits());
            return bb.array();
        }
    }

    @ReadingConverter
    public static class ByteArrayToUuidConverter implements Converter<byte[], UUID> {
        @Override
        public UUID convert(byte[] source) {
            if (source == null || source.length != 16) {
                return null;
            }
            ByteBuffer bb = ByteBuffer.wrap(source);
            long mostSigBits = bb.getLong();
            long leastSigBits = bb.getLong();
            return new UUID(mostSigBits, leastSigBits);
        }
    }
}
