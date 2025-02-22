import Comment from "../blog/comment.model";
import Role from "./role.model";
import Post from "../blog/post.model";

export default interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  accountLocked: boolean;
  enabled: boolean;
  posts?: Post[];
  comments?: Comment[];
  roles: Role[];
}
