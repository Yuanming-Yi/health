package com.itheima.test;

public class DemoTest {
    public static void main(String[] args) {

        User user = new User();
        user.setName("zhangsan");
        User user2 = new User();

        user2=user;
        user2.setName("lisi");

        System.out.println(user);

    }

    static class User{
        private String name;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
