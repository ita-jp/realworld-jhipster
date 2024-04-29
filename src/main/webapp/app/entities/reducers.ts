import profile from 'app/entities/profile/profile.reducer';
import article from 'app/entities/article/article.reducer';
import comment from 'app/entities/comment/comment.reducer';
import tag from 'app/entities/tag/tag.reducer';
import userExtended from 'app/entities/user-extended/user-extended.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  profile,
  article,
  comment,
  tag,
  userExtended,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
