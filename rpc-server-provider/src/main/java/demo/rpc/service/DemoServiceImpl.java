package demo.rpc.service;

/**
 * @author yellow
 * @date 2019/9/12 14:36
 * 温馨提醒:
 * 代码千万行，
 * 注释第一行。
 * 命名不规范，
 * 同事两行泪。
 */
public class DemoServiceImpl implements IDemoService {

    @Override
    public String welcomeFun(String name) {
        return "hello,welcome "+name+", RPC project is ok!";
    }
}
