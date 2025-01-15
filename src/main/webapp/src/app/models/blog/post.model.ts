import Author from "./author.model";

export default interface Post {
  id: number;
  text: string;
  imageCover: string;
  publishedOn: Date;
  numberOfComments: number;
  author: Author;
}
