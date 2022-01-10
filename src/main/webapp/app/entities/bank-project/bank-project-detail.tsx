import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-project.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankProjectDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankProjectEntity = useAppSelector(state => state.bankProject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankProjectDetailsHeading">
          <Translate contentKey="akountsApp.bankProject.detail.title">BankProject</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankProjectEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="akountsApp.bankProject.name">Name</Translate>
            </span>
          </dt>
          <dd>{bankProjectEntity.name}</dd>
          <dt>
            <span id="projectType">
              <Translate contentKey="akountsApp.bankProject.projectType">Project Type</Translate>
            </span>
          </dt>
          <dd>{bankProjectEntity.projectType}</dd>
          <dt>
            <span id="initialValue">
              <Translate contentKey="akountsApp.bankProject.initialValue">Initial Value</Translate>
            </span>
          </dt>
          <dd>{bankProjectEntity.initialValue}</dd>
          <dt>
            <span id="currentValue">
              <Translate contentKey="akountsApp.bankProject.currentValue">Current Value</Translate>
            </span>
          </dt>
          <dd>{bankProjectEntity.currentValue}</dd>
        </dl>
        <Button tag={Link} to="/bank-project" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-project/${bankProjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankProjectDetail;
