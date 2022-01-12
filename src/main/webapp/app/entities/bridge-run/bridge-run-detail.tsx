import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bridge-run.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BridgeRunDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bridgeRunEntity = useAppSelector(state => state.bridgeRun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bridgeRunDetailsHeading">
          <Translate contentKey="akountsApp.bridgeRun.detail.title">BridgeRun</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bridgeRunEntity.id}</dd>
          <dt>
            <span id="runType">
              <Translate contentKey="akountsApp.bridgeRun.runType">Run Type</Translate>
            </span>
          </dt>
          <dd>{bridgeRunEntity.runType}</dd>
          <dt>
            <span id="runStatus">
              <Translate contentKey="akountsApp.bridgeRun.runStatus">Run Status</Translate>
            </span>
          </dt>
          <dd>{bridgeRunEntity.runStatus}</dd>
          <dt>
            <span id="runStart">
              <Translate contentKey="akountsApp.bridgeRun.runStart">Run Start</Translate>
            </span>
          </dt>
          <dd>{bridgeRunEntity.runStart ? <TextFormat value={bridgeRunEntity.runStart} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="runEnd">
              <Translate contentKey="akountsApp.bridgeRun.runEnd">Run End</Translate>
            </span>
          </dt>
          <dd>{bridgeRunEntity.runEnd ? <TextFormat value={bridgeRunEntity.runEnd} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/bridge-run" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bridge-run/${bridgeRunEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BridgeRunDetail;
