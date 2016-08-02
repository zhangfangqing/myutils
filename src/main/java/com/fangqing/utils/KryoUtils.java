package com.fangqing.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * @功能 TODO
 *
 * @author zhangfangqing
 * @date 2016年8月2日
 * @time 下午2:19:03
 */
public final class KryoUtils {
	private static final KryoFactory FACTORY = new KryoFactory() {
		public Kryo create() {
			Kryo kryo = new Kryo();
			kryo.setAsmEnabled(true);
			kryo.setAutoReset(true);
			kryo.setReferences(true);

			return kryo;
		}
	};

	private static final KryoPool POOL = new KryoPool.Builder(FACTORY).softReferences().build();

	public static Kryo get() {
		return POOL.borrow();
	}

	public static void release(Kryo kryo) {
		POOL.release(kryo);
	}

	public static byte[] serialize(Object obj) {
		Kryo kryo = get();
		Output output = new Output(2048, -1);
		kryo.writeClassAndObject(output, obj);
		byte[] data = output.toBytes();
		output.close();
		release(kryo);
		return data;
	}

	public static Object deserialize(byte[] data) {
		Kryo kryo = get();
		Input input = new Input(data);
		Object object = kryo.readClassAndObject(input);
		input.close();
		release(kryo);
		return object;
	}
}