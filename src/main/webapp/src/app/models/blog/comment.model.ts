import Author from "./author.model";

export default interface Comment {
  id: number;
  text: string;
  publishedOn: Date;
  author: Author;
}
