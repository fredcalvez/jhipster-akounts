import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './process-run.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProcessRunDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const processRunEntity = useAppSelector(state => state.processRun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="processRunDetailsHeading">
          <Translate contentKey="akountsApp.processRun.detail.title">ProcessRun</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.id}</dd>
          <dt>
            <span id="process">
              <Translate contentKey="akountsApp.processRun.process">Process</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.process}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="akountsApp.processRun.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.parentId}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="akountsApp.processRun.status">Status</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.status}</dd>
          <dt>
            <span id="returns">
              <Translate contentKey="akountsApp.processRun.returns">Returns</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.returns}</dd>
          <dt>
            <span id="error">
              <Translate contentKey="akountsApp.processRun.error">Error</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.error}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="akountsApp.processRun.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {processRunEntity.startTime ? <TextFormat value={processRunEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="akountsApp.processRun.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{processRunEntity.endTime ? <TextFormat value={processRunEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/process-run" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/process-run/${processRunEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProcessRunDetail;
