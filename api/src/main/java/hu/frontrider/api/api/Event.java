package hu.frontrider.api.api;

interface Event {

    <T> T getMember(Class<T> memberType);
}
