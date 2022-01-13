import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaid-run.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidRunDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaidRunEntity = useAppSelector(state => state.plaidRun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaidRunDetailsHeading">
          <Translate contentKey="akountsApp.plaidRun.detail.title">PlaidRun</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaidRunEntity.id}</dd>
          <dt>
            <span id="runType">
              <Translate contentKey="akountsApp.plaidRun.runType">Run Type</Translate>
            </span>
          </dt>
          <dd>{plaidRunEntity.runType}</dd>
          <dt>
            <span id="runStatus">
              <Translate contentKey="akountsApp.plaidRun.runStatus">Run Status</Translate>
            </span>
          </dt>
          <dd>{plaidRunEntity.runStatus}</dd>
          <dt>
            <span id="runItemId">
              <Translate contentKey="akountsApp.plaidRun.runItemId">Run Item Id</Translate>
            </span>
          </dt>
          <dd>{plaidRunEntity.runItemId}</dd>
          <dt>
            <span id="runStart">
              <Translate contentKey="akountsApp.plaidRun.runStart">Run Start</Translate>
            </span>
          </dt>
          <dd>{plaidRunEntity.runStart ? <TextFormat value={plaidRunEntity.runStart} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="runEnd">
              <Translate contentKey="akountsApp.plaidRun.runEnd">Run End</Translate>
            </span>
          </dt>
          <dd>{plaidRunEntity.runEnd ? <TextFormat value={plaidRunEntity.runEnd} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/plaid-run" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaid-run/${plaidRunEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaidRunDetail;
