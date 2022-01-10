import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankCategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankCategoryEntity = useAppSelector(state => state.bankCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankCategoryDetailsHeading">
          <Translate contentKey="akountsApp.bankCategory.detail.title">BankCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankCategoryEntity.id}</dd>
          <dt>
            <span id="parent">
              <Translate contentKey="akountsApp.bankCategory.parent">Parent</Translate>
            </span>
          </dt>
          <dd>{bankCategoryEntity.parent}</dd>
          <dt>
            <span id="categorieLabel">
              <Translate contentKey="akountsApp.bankCategory.categorieLabel">Categorie Label</Translate>
            </span>
          </dt>
          <dd>{bankCategoryEntity.categorieLabel}</dd>
          <dt>
            <span id="categorieDesc">
              <Translate contentKey="akountsApp.bankCategory.categorieDesc">Categorie Desc</Translate>
            </span>
          </dt>
          <dd>{bankCategoryEntity.categorieDesc}</dd>
          <dt>
            <span id="income">
              <Translate contentKey="akountsApp.bankCategory.income">Income</Translate>
            </span>
          </dt>
          <dd>{bankCategoryEntity.income ? 'true' : 'false'}</dd>
          <dt>
            <span id="isexpense">
              <Translate contentKey="akountsApp.bankCategory.isexpense">Isexpense</Translate>
            </span>
          </dt>
          <dd>{bankCategoryEntity.isexpense ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/bank-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-category/${bankCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankCategoryDetail;
