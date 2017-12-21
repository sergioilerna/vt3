package cat.udl.eps.ep.data;

public class Ticket {
    private String name;

    public Ticket(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return name != null ? name.equals(ticket.name) : ticket.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ticket nom = " + name;
    }
}
