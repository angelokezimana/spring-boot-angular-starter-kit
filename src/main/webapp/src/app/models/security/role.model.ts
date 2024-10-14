import Permission from "./permission.model";

export default interface Role {
  id: number;
  name: string;
  permissions: Permission[];
}
