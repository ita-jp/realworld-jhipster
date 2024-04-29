import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Profile from './profile';
import Article from './article';
import Comment from './comment';
import Tag from './tag';
import UserExtended from './user-extended';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="profile/*" element={<Profile />} />
        <Route path="article/*" element={<Article />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="tag/*" element={<Tag />} />
        <Route path="user-extended/*" element={<UserExtended />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
