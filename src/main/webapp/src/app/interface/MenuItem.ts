export default interface MenuItem {
  name: string;
  link: string;
  icon: string;
  submenu: MenuItem[];
}
