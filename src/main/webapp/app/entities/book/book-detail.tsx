import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './book.reducer';

export const BookDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bookEntity = useAppSelector(state => state.book.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.book.detail.title">Book</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleApplicationApp.book.name">Name</Translate>
            </span>
          </dt>
          <dd>{bookEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.book.description">Description</Translate>
            </span>
          </dt>
          <dd>{bookEntity.description}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.book.author">Author</Translate>
          </dt>
          <dd>{bookEntity.author ? bookEntity.author.secondName : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.book.genre">Genre</Translate>
          </dt>
          <dd>{bookEntity.genre ? bookEntity.genre.genreName : ''}</dd>
        </dl>
        <Button tag={Link} to="/book" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/book/${bookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookDetail;
