import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-stream.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankStreamDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankStreamEntity = useAppSelector(state => state.bankStream.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankStreamDetailsHeading">
          <Translate contentKey="akountsApp.bankStream.detail.title">BankStream</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankStreamEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="akountsApp.bankStream.name">Name</Translate>
            </span>
          </dt>
          <dd>{bankStreamEntity.name}</dd>
          <dt>
            <span id="streamType">
              <Translate contentKey="akountsApp.bankStream.streamType">Stream Type</Translate>
            </span>
          </dt>
          <dd>{bankStreamEntity.streamType}</dd>
        </dl>
        <Button tag={Link} to="/bank-stream" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-stream/${bankStreamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankStreamDetail;
