package de.cloud.network.packet;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record NetworkBuf(ByteBuf byteBuf) {

    public NetworkBuf writeString(String string) {
        final var bytes = string.getBytes(StandardCharsets.UTF_8);
        this.byteBuf.writeInt(bytes.length);
        this.byteBuf.writeBytes(bytes);
        return this;
    }

    public String readString() {
        final var bytes = new byte[this.byteBuf.readInt()];
        this.byteBuf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T readEnum() {
        final var nullObject = this.byteBuf.readBoolean();
        if (nullObject) return null;
        Class<?> enumClass;
        try {
            final var classString = this.readString();
            enumClass = Class.forName(classString);
            final var varInt = this.readVarInt();
            return (T) enumClass.getEnumConstants()[varInt];
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NetworkBuf writeEnum(Enum<?> val) {
        this.byteBuf.writeBoolean(val == null);
        if (val != null) {
            this.writeString(val.getDeclaringClass().getName());
            this.writeVarInt(val.ordinal());
        }
        return this;
    }

    public void writeVarInt(int input) {
        while ((input & -128) != 0) {
            this.byteBuf.writeByte(input & 127 | 128);
            input >>>= 7;
        }
        this.byteBuf.writeByte(input);
    }

    public int readInt() {
        return this.byteBuf.readInt();
    }

    public double readDouble() {
        return this.byteBuf.readDouble();
    }

    public boolean readBoolean() {
        return this.byteBuf.readBoolean();
    }

    public NetworkBuf writeBoolean(boolean value) {
        this.byteBuf.writeBoolean(value);
        return this;
    }

    public NetworkBuf writeInt(int value) {
        this.byteBuf.writeInt(value);
        return this;
    }

    public NetworkBuf writeDouble(double value) {
        this.byteBuf.writeDouble(value);
        return this;
    }

    public int readVarInt() {
        var i = 0;
        var j = 0;

        while (true) {
            var b0 = this.byteBuf.readByte();
            i |= (b0 & 127) << j++ * 7;

            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((b0 & 128) != 128) {
                break;
            }
        }
        return i;
    }

    public UUID readUUID() {
        return new UUID(this.byteBuf.readLong(), this.byteBuf.readLong());
    }

    public NetworkBuf writeUUID(UUID uniqueId) {
        this.byteBuf.writeLong(uniqueId.getMostSignificantBits());
        this.byteBuf.writeLong(uniqueId.getLeastSignificantBits());
        return this;
    }

}
