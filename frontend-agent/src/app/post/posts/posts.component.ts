import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/auth/auth/service/auth.service';
import { NewCommentComponent } from 'src/app/comment/new-comment/new-comment.component';
import { CommentService } from 'src/app/comment/new-comment/service/comment.service';
import { Post } from 'src/model/post';
import { NewPostComponent } from './create/new-post/new-post.component';
import { PostService } from './service/post.service';
import { interval } from 'rxjs';
import { Comment } from '../../../model/comment';
import { EditCommentComponent } from 'src/app/comment/edit/edit-comment/edit-comment.component';
import { ToastrService } from 'ngx-toastr';
import { Input } from '@angular/core';
import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { AfterViewInit } from '@angular/core';
import { StarRatingComponent } from 'ng-starrating/components/star-rating/star-rating.component';
import { RateService } from './service/rate.service';
import { Rate } from 'src/model/rate';
import { Subscription } from 'rxjs/internal/Subscription';



@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})



export class PostsComponent implements OnInit, AfterViewInit {


  posts: Post[] = [];
  isAdd: boolean = true;
  par: any;
  userList: any[] = [];
  user: any;
  isEdit: boolean = true;
  comment: Comment;
  commentsNumber: Comment[] = [];
  comments: Comment[] = [];
  yes: boolean = false;
  size: "24px";
  value: 0;
  readonly: false;
  rate: number;
  r: Rate = new Rate();
  source: any;
  subscription: Subscription;

  constructor(private postService: PostService,
    public dialog: MatDialog,
    private authService: AuthService,
    private commentService: CommentService,
    private toastr: ToastrService,
    private cdr: ChangeDetectorRef,
    private rateService: RateService,
    @Inject(MAT_DIALOG_DATA) public data: any) {

  }

  ngOnInit(): void {

    this.postService.allPosts().subscribe(
      data => {
        this.posts = data;
        console.log(this.posts)
      }
    )

    this.par = this.authService.getToken();


    this.authService.whoami(this.par).subscribe(
      data =>
        this.user = data
    )

    this.authService.getAllUsers().subscribe((data: any[]) => {
      this.userList = data;
    })

    interval(120000).subscribe(val =>
      this.postService.newPosts(this.user.id).subscribe(
        data => {
          console.log(data)
          if (data[0] == null) {
            this.toastr.info('There is no new posts.', 'Info')
          } else {
            this.toastr.info('There is new posts!', 'Info')
          }
        }

      )
    );

    interval(120000).subscribe(val =>
      this.postService.newComments(this.user.id).subscribe(
        data => {
          console.log(data)
          if (data[0] == null) {
            this.toastr.info('There is no new comments.', 'Info')
          } else {
            this.toastr.info('There is new comments!', 'Info')
          }
        }

      )
    );

  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }


  openModalDialog(): void {
    const dialogRef = this.dialog.open(NewPostComponent, {
      width: '500px',
      height: '400px',
      data: { isAdd: this.isAdd = true }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.postService.allPosts().subscribe(
        data => {
          this.posts = data;
          console.log(this.posts)
        }
      )
    });


  }

  editPost(post: Post): void {
    const dialogRef = this.dialog.open(NewPostComponent, {
      width: '500px',
      height: '400px',
      data: post
    });
    dialogRef.afterClosed().subscribe(result => {
      this.postService.allPosts().subscribe(
        data => {
          this.posts = data;
          console.log(this.posts)
        }
      )
    });

  }

  deletePost(post: Post) {
    console.log(this.user + ' ' + post.user)
    if (this.user.username == post.user) {
      this.postService.delete(post.id).subscribe(
        data => {

          this.postService.allPosts().subscribe(
            data => {
              this.posts = data;
              console.log(this.posts)
            }
          )
          this.toastr.success('You have successfully deleted Post!', 'Success')
        }
      )
    } else {
      this.toastr.error('You cannot delete a ' + 'post that is not from you', 'Error');
    }
  }

  commentPost(post: Post) {
    const dialogRef = this.dialog.open(NewCommentComponent, {
      width: '500px',
      height: '200px',
      data: post
    });
    dialogRef.afterClosed().subscribe(result => {

      this.postService.allPosts().subscribe(
        data => {
          this.posts = data;
          console.log(this.posts)
        }
      )
    });
  }



  editComment(comment: Comment): void {

    const dialogRef = this.dialog.open(EditCommentComponent, {
      width: '500px',
      height: '400px',
      data: comment
    });
    dialogRef.afterClosed().subscribe(result => {
      this.postService.allPosts().subscribe(
        data => {
          this.posts = data;
          console.log(this.posts)
        }
      )
    });
  }

  deleteComment(comment: Comment) {
    if (comment.userName == this.user.username) {
      this.commentService.delete(comment.id).subscribe(
        data => {

          this.postService.allPosts().subscribe(
            data => {
              this.posts = data;
              console.log(this.posts)
            }
          )
          this.toastr.success('You have successfully deleted comment!', 'Success')
        }

      )
    } else {
      this.toastr.error('You cannot delete a ' + 'comment that is not from you', 'Error');
    }
  }



  getUsername(post: Post) {
    return post?.user;
  }

  getUser(post: Post) {
    this.postService.getUser(post).subscribe(
      data => {
        console.log(data)
      }
    )

  }

  getAllUsers() {
    this.authService.getAllUsers().subscribe(
      data => {
        console.log(data)
      }
    )
  }

  getDate(comment: Comment) {
    return comment?.commentDate
  }

  getNumber(id: number) {
    const post = this.posts.find(x => x.id == id);
    this.commentsNumber = post.comments.filter(item =>
      item.deleted == false)
    return this.commentsNumber.length;
  }

  onRate($event: { oldValue: number, newValue: number, starRating: StarRatingComponent }, post: Post) {
    this.rate = $event.newValue;
    this.r.rate = this.rate;
    this.r.userName = this.user.username;
    if (this.user.username != post.user) {
      this.rateService.add(post.id, this.r).subscribe(
        data => {
          this.postService.allPosts().subscribe(
            data => {
              this.posts = data;
              this.toastr.success('You have successfully rated post!', 'Success')
            }
          )
        }
      )
    } else {
      this.toastr.error('Error', 'Error');
    }

  }

  getAvg(post: Post) {
    if (post.rates.length == 0) {
      return 0;
    } else {
      let sum = 0;
      post.rates.forEach(element => {
        sum += element.rate;
      });

      return (sum / post.rates.length).toFixed(1);
    }
  }

  checkNews() {
    console.log(this.user.username)
    this.postService.newPosts(this.user.id).subscribe(
      data => {
        console.log(data)
      }
    )
  }



}
