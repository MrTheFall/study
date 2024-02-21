package characters.groups;

import java.util.ArrayList;
import java.util.List;
import exceptions.CantAttendException;

public class Crowd {
    private List<Member> members = new ArrayList<>();

    // Статический вложенный класс Member
    public static class Member {
        private String name;
        public Member(String name) {
            this.name = name;
            if (name.equals("Карта Король")) {
                throw new CantAttendException("Короли не присоединяются к толпе");
            }
        }

        public void attend() {
            System.out.println(this.name + " собирается в толпу");
        }
    }

    // Метод для добавления нового участника в толпу
    public void addMember(Member member) {
        members.add(member);
    }

    // Метод для иллюстрации присутствия толпы
    public void gather() {
        for (Member member : members) {
            member.attend();
        }
    }
}
