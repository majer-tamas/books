export function showPassword(currentlyTypedPassword: string) {
  const x = document.getElementById(currentlyTypedPassword);
  if (x) {
    const c = x.nextElementSibling;
    if (c) {
      if (x.getAttribute('type') == 'password') {
        c.removeAttribute('class');
        c.setAttribute('class', 'fas fa-eye');
        x.removeAttribute('type');
        x.setAttribute('type', 'text');
      } else {
        x.removeAttribute('type');
        x.setAttribute('type', 'password');
        c.removeAttribute('class');
        c.setAttribute('class', 'fas fa-eye-slash');
      }
    }
  }
}
